(ns feedeen-stat.core
  (:gen-class)
  (:require [clj-http.client :as client]
            [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key public-key]]
            [clj-time.core :refer [now plus hours minutes]]
            [clj-time.coerce :refer [to-epoch]]
            [clojure.data.json :as json]))


(defn feedeen-api-url
  [path]
  (str
    ; "http://localhost:8000/api"
    "https://www.feedeen.com/api"
    path))


(defn make-claim
  [dev-client-id]
  {:iss dev-client-id
   :aud (feedeen-api-url "/auth")
   :exp (-> (now) (plus (hours 1)))})

(defn make-jwt-token
  [claim dev-secret]
  (-> claim jwt (sign :HS256 dev-secret) to-str))

(defn get-access-token
  [jwt-token]
  (->
    "/auth"
    feedeen-api-url
    (client/post
      {:as :x-www-form-urlencoded
       :form-params {:grant_type "urn:ietf:params:oauth:grant-type:jwt-bearer"
                     :assertion jwt-token}})
    :body
    (json/read-str :key-fn keyword)
    :access_token))

(defn api-request
  [access-token path]
  (-> path
      feedeen-api-url
      (client/get
        {:headers {:Authorization (str "Bearer " access-token)}})
      :body
      (json/read-str :key-fn keyword)))

(defn get-feeds
  [access-token]
  (api-request access-token "/api/feeds"))

(defn get-items-count
  [access-token & {:keys [mode] :or {mode "default"}}]
  (api-request access-token
               (str "/items/count?mode=" mode)))


(defn -main
  "main"
  ([]
   (println "Usage: $0 <CLIENT_ID> <SECRET>")
   (System/exit 1))
  ([dev-client-id dev-secret]
   (let [access-token (-> (make-claim dev-client-id) (make-jwt-token dev-secret) get-access-token)
         total (-> (get-items-count access-token :mode "total") :total)]
     (print total))))
