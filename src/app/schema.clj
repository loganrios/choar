(ns app.schema)

(def schema
  {:user/id :uuid
   :user [:map {:closed true}
          [:xt/id                     :user/id]
          [:user/email                :string]
          [:user/joined-at            inst?]
          [:user/foo {:optional true} :string]
          [:user/bar {:optional true} :string]]

   :msg/id :uuid
   :msg [:map {:closed true}
         [:xt/id       :msg/id]
         [:msg/user    :user/id]
         [:msg/text    :string]
         [:msg/sent-at inst?]]})

(def choar-schema
  {:chore/id :uuid
   :chore [:map
           [:xt/id :chore/id]
           [:title :string]
           [:actions [:vector :action/id]]]

   :action/id :uuid
   :action [:map
            [:xt/id :action/id]
            [:what :string]
            [:why :string]
            [:how [:vector :string]]
            [:trigger :trigger/id]
            [:result :string]
            [:uses [:vector :supply/id]]]

   :trigger/id :uuid
   :trigger [:map
             [:xt/id :trigger/id]
             [:seasonality [:map [:start inst?] [:end inst?]]]
             [:recurrence pos-int?] ;; in weeks, minimum 1 
             ;; `noted` is default
             ]

   :assignee/id :uuid
   :assignee [:map
              [:xt/id :assignee/id]
              [:name :string]]
   
   :duty/id :uuid
   :duty [:map
          [:xt/id :duty/id]
          [:when inst?]
          [:who :assignee/id]
          [:tasks [:vector :task/id]]]

   :task/id :uuid
   :task [:map
          [:xt/id :task/id]
          [:action :action/id]
          [:status [:enum :blocked :ready :underway :completed :deferred]]
          [:verification :verification/id]
          [:notes [:vector :string]]]

   :verification/id :uuid
   :verification [:map
                  [:xt/id :verification/id]
                  [:verifier :assignee/id]
                  [:on inst?]]

   :supply/id :uuid
   :supply [:map
            [:xt/id :supply/id]
            [:what :string]
            [:depleted? :boolean]
            [:limited? :boolean]]})

(def module
  {:schema schema})
