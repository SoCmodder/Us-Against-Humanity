TAAHServer::Application.routes.draw do
  
  ActiveAdmin.routes(self)

  devise_for :admin_users, ActiveAdmin::Devise.config

  namespace :api do
    namespace :v1 do
      devise_scope :user do
        post 'registrations' => 'registrations#create', :as => 'register'
        post 'sessions' => 'sessions#create', :as => 'login'
        delete 'sessions' => 'sessions#destroy', :as => 'logout'
      end
      #Leftover from demo will be deleted
      get 'tasks' => 'tasks#index', :as => 'tasks'
      post 'tasks' => 'tasks#create'
      put 'tasks/:id/open' => 'tasks#open', :as => 'open_task'
      put 'tasks/:id/complete' => 'tasks#complete', :as => 'complete_task'

      #User paths
      #Returns all user information
      get 'users/:id/find' => 'users#find', :as => 'find_user' #Tested-complete
      #Returns the user_id
      get 'users' => 'users#email' #Tested-complete
      #Gets the user's stats
      #get 'users/:id/stats' => "users#stats", :as => 'stat_user'

      #Game paths
      #Creates game
      post 'games' => 'games#create' #Tested-complete
      #Add current user to game
      put 'games/:id' => 'games#adduser', :as => 'adduser_game' #Tested-complete
      #Remove user from game
      delete 'games/:id/users/:user_id' =>  'games#removeuser', :as => 'deleteuser_game' #Tested-complete
      #Plays card(s) from user's hand
      put 'games/:id/whitecard' => 'games#playwhite'  
      #Gets all submitted cards
      #get 'games/:id/white' => 'games#getsubmitted', :as => 'whitecard_game'
      #Submittes the winning card
      #put 'games/:id/winningcard' => 'games#winningcard', :as => 'winningcard_game'
      #Gets the black card for the round
      get 'games/:id/blackcard' => 'games#blackcard' #Tested-complete
      #Gets the current user's hand
      get 'games/:id/hand' => 'games#gethand' #Tested-complete
      #Gets the user's won black card
      #get 'games/:id/users/:user_id/blackcards' => 'games#getwon', :as => 'won_game'
      #Gets the users score
      #get 'games/:id/score' => 'games#score', :as => 'score_game'
      #Gets information on previous round
      #get 'game/:id/last_round' => 'games#lastround'


    end
  end

  devise_for :users

  # The priority is based upon order of creation:
  # first created -> highest priority.

  # Sample of regular route:
  #   match 'products/:id' => 'catalog#view'
  # Keep in mind you can assign values other than :controller and :action

  # Sample of named route:
  #   match 'products/:id/purchase' => 'catalog#purchase', :as => :purchase
  # This route can be invoked with purchase_url(:id => product.id)

  # Sample resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Sample resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Sample resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Sample resource route with more complex sub-resources
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', :on => :collection
  #     end
  #   end

  # Sample resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end

  # You can have the root of your site routed with "root"
  # just remember to delete public/index.html.
  # root :to => 'welcome#index'

  # See how all your routes lay out with "rake routes"

  # This is a legacy wild controller route that's not recommended for RESTful applications.
  # Note: This route will make all actions in every controller accessible via GET requests.
  # match ':controller(/:action(/:id))(.:format)'
end
