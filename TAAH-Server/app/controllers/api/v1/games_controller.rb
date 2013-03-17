class Api::V1::GamesController < ApplicationController
  skip_before_filter :verify_authenticity_token,
                     :if => Proc.new { |c| c.request.format == 'application/json' }

  # Just skip the authentication for now
  before_filter :authenticate_user!

  respond_to :json

  #Returns all games that the user is hosting
  def index
    @games = current_user.games
  end

  def open_games
    @games = Game.find_all_by_state(nil)
  end

  def create
  	@game = current_user.games.build(params[:game])
  	if @game.save
      @gameuser = current_user.gameusers.build(:game_id => @game.id)
      if @gameuser.save
  		  @game
      end
  	else
  		render :status => :unprocessable_entity,
            :json => { :success => false,
                      :info => @game.errors,
                      :data => {} }
   	end
  end

  def adduser
    @game = Game.find(params[:id])
    if @game      
      if @game.state == nil
        if @game.private
          #TODO gameinvite
        end
        @alreayin = Gameuser.find_by_game_id_and_user_id(@game.id, current_user.id)
        if @alreayin
          render :status => 405,
                 :json => { :success => false,
                            :info => "Already In It",
                            :data => {}}
        else
          @gameuser = current_user.gameusers.build(:game_id => @game.id)    
          if @gameuser.save
            @listusers = @game.gameusers.all
            if @listusers.size == @game.slots
              @game.start!
            end
            @gameuser
          else
            render :status => :unprocessable_entity,
                   :json => { :success => false,
                              :info => @gameuser.errors,
                              :data => {} }

          end
        end        
      else
        render :status => 405,
             :json => { :success => false,
             :info => "Game Already Started",
             :data => {}}
      end
    else
      render :status => 404,
             :json => { :success => false,
             :info => "Game Not Found",
             :data => {}}
    end
  end

  def removeuser
    @game = Game.find(params[:id])
    #Checks to see if the game is active and the user is either the host or 
    # a user in the game
    if current_user.id == params[:user_id] || @game.user_id == current_user.id
      if !(@game.state == 2)
        @game.removeuser(params[:user_id])
      else
         render :status => 409,
             :json => { :success => false,
                        :info => "Game is closed",
                        :data => {} }
      end
    else
       render :status => 409,
             :json => { :success => false,
                        :info => "Cannot remove user",
                        :data => {} }
    end
  end
end