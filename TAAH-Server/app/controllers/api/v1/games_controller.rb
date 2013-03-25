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
        @allowedin = true
        if @game.private
          #TODO gameinvite
        end
        if @allowedin
          @alreayin = Gameuser.find_by_game_id_and_user_id(@game.id, current_user.id)
          if @alreayin
            render :status => 405,
                   :json => { :success => false,
                              :info => "Already In It",
                              :user_id => current_user.id,
                              :data => {}}
          else
            @gameuser = current_user.gameusers.build(:game_id => @game.id)    
            if @gameuser.save
              @listusers = @game.gameusers.all
              @gameuser
              if @listusers.size == @game.slots
                @game.start!
              end
            else
              render :status => :unprocessable_entity,
                     :json => { :success => false,
                                :info => @gameuser.errors,
                                :data => {} }
            end
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

  def gethand
    @game = Game.find(params[:id])
    @gameuser = Gameuser.find_by_game_id_and_user_id(@game.id, current_user.id)
    @ids = @game.whitecardinhand.find_all_by_gameuser_id(@gameuser.id)
    @wcids = @ids.map(&:whitecard_id)
    @text = Whitecard.find(:all, :conditions => { :id => @wcids } ).map(&:text)

    render :status => 200,
           :json => { :success => true,
                      :data => { :ids => @ids.map(&:id),
                                 :texts => @text} }
  end

  def blackcard
    @game = Game.find(params[:id])
    @blackcard = Blackcard.find(@game.current_black)
    render :status => 200,
           :json => { :success => true,
                      :data => { :text => @blackcard.text,
                                 :numwhite => @blackcard.numwhite } }
  end

  def playwhite
    @game = Game.find(params[:id])
    @gameuser = @game.gameusers.find_by_user_id(current_user.id)
    @whiteids = @game.whitecardinhand.find_all_by_gameuser_id(@gameuser.id).map(&:id)
    @subids = params[:card_id]
    @numwhite = Blackcard.find(@game.current_black).numwhite
    @allusers = @game.gameusers
    if current_user.id != @game.gameusers[@game.user_turn].user_id
      if @subids.length == @numwhite
        if (@whiteids & @subids) == @subids
          @whitecards = @game.whitecardinhand.find(@subids)
          ActiveRecord::Base.transaction do
            @whitecards.each do |card|
              @game.whitecardinplay.create(:whitecard_id => card.whitecard_id, :gameuser_id => @gameuser.id)
              card.destroy
            end
          end
          if(@game.whitecardindeck.count < @subids.length)
            @alldiscard = @game.whitecardindiscard
            ActiveRecord::Base.transaction do
              @allcard.each do |card|
                @game.whitecardindeck.create(:whitecard_id => card.whitecard_id)
                card.destroy
              end
            end
          end
          hand = @game.whitecardindeck.all.sample(@subids.length)
          ActiveRecord::Base.transaction do
            hand.each do |card|
              @game.whitecardinhand.create(:gameuser_id => @gameuser.id, :whitecard_id => card.whitecard_id)
              card.destroy
            end
          end
        else
          render :status => :unprocessable_entity,
                            :json => { :success => false,
                                       :info => "Invalid Cards",
                                       :data => { :Invalid_ids => @subids - @whiteids } }
        end
      else
        render :status => :unprocessable_entity,
                     :json => { :success => false,
                                :info => "Invalid Number of Cards",
                                :data => { :numwhite => @numwhite } }
      end
    end
  end

  def getsubmitted
    @game = Game.find(params[:id])
    if current_user.id = @game.gameusers[@game.user_turn].user_id
      @numsubmit = @game.whitecardinplay.map(&:gameuser_id).uniq.length
      if @numsubmit == (@game.slots - 1)
        @submit = Struct.new(:gameuser_id, :whitetext)
        @submitArray = [];
        @game.gameusers.each do |gameuser|
           @ids = @game.whitecardinplay.find_all_by_gameuser_id(gameuser.id)
           @wcids = @ids.map(&:whitecard_id)
           @text = Whitecard.find(:all, :conditions => { :id => @wcids } ).map(&:text)
           @temp = @submit.new(gameuser.id, @text)
           @submitArray.push(@temp)
         end
         render :status => 200,
                :json => { :success => true,
                           :data => { :submitted => @submitArray } }

      else
        render :status => :unprocessable_entity,
               :json => { :success => false,
                          :info => "Not all users have submitted cards",
                          :data => {} }
      end
    else

      render :status => :unprocessable_entity,
             :json => { :success => false,
                        :info => "It is not your turn",
                        :data => {} }

    end

    
  end

  def winningcard
    @game = Game.find(params[:id])
    @winninguser = Gameuser.find(params[:user_id])
    if current_user.id == @game.gameusers[@game.user_turn].user_id
      ActiveRecord::Base.transaction do
        @winninguser.update_attribute(:score, @winninguser.score + 1)
        @game.blackcardwon.create(:gameuser_id => @winninguser.id, :blackcard_id => @game.current_black)        
        @gamelast = @game.gamelasts.create(:gameuser_id => @winninguser.id, :blackcard_id => @game.current_black)
        @game.whitecardinplay.each do |card|
          @gamelast.gamelastwhite.create(:gameuser_id => card.gameuser_id, :whitecard_id => card.whitecard_id)
          @game.whitecardindiscard.create(:whitecard_id => card.whitecard_id)
          card.destroy
        end
        if @winninguser.score == @game.points_to_win
          @game.update_attribute(:state, 2)
          @game.end!
        else
          @game.next!
        end
      end
      render :status => 200,
             :json => { :success => true, 
                        :info => "Next turn",
                        :data => {} }
    else
      render :status => 409,
             :json => { :success => false,
                        :info => "It is not your turn",
                        :data => {} }
    end
  end

  def getwon
    @game = Game.find(params[:id])
    @bids = @game.blackcardwon.find_all_by_gameuser_id(params[:user_id]).map(&:blackcard_id)
    @blacktext = Blackcard.find(:all, :conditions => { :id => @bids}).map(&:text)

    render :status => 200,
           :json => { :success => true,
                      :data => { :text => @blacktext }}
  end

  def score
    @game = Game.find(params[:id])
    @submit = Struct.new(:gameuser_id, :score)
    @submitArray = []
    @game.gameusers.each do |gameuser|
      @temp = @submit.new(gameuser.id, gameuser.score)
      @submitArray.push(@temp)
    end
    render :status => 200,
           :json => { :success => true,
                      :data => { :score => @submitArray } }
  end

  def lastround
    @game = Game.find(params[:id])
    @gameusers = @game.gameusers 
    @gamelast = @game.gamelasts.last
    @blacktext = Blackcard.find(@gamelast.blackcard_id).text
    @submit = Struct.new(:gameuser_id, :Whitecardtext)
    @submitArray = []
    @gameusers.each do |gameuser|
      @wcids = @gamelast.gamelastwhite.find_all_by_gameuser_id(gameuser.id).map(&:whitecard_id)
      @whitetexts = Whitecard.find(:all, :conditions => { :id => @wcids}).map(&:text)
      @temp = @submit.new(gameuser.id, @whitetexts)
      @submitArray.push(@temp)
    end 
    render :status => 200,
           :json => { :success => true,
                      :data => { :black_card => @blacktext,
                                 :winninguser => @gamelast.gameuser_id,
                                 :submitted => @submitArray}}
  end

  def history
    @game = Game.find(params[:id])
    @gameusers = @game.gameusers
    @gamelasts = @game.gamelasts
    @round = Struct.new(:blacktext, :winninguser, :submitted)
    @roundArray = []
    @submit = Struct.new(:gameuser_id, :Whitecardtext)
    @gamelasts.each do |gamelast|
      @blacktext = Blackcard.find(gamelast.blackcard_id).text
      @submitArray = []
      @gameusers.each do |gameuser|
        @wcids = gamelast.gamelastwhite.find_all_by_gameuser_id(gameuser.id).map(&:whitecard_id)
        @whitetexts = Whitecard.find(:all, :conditions => { :id => @wcids}).map(&:text)
        @temp = @submit.new(gameuser.id, @whitetexts)
        @submitArray.push(@temp)
      end
      @tempRound = @round.new(@blacktext, gamelast.gameuser_id, @submitArray)
      @roundArray.push(@tempRound)
    end
    render :status => 200,
           :json => { :success => true,
                      :data => { :rounds => @roundArray } }
  end


    
end