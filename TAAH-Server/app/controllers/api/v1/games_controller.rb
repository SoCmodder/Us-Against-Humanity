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
    @games = Game.find_all_by_state(0)
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

  def find
    @critera = params[:find]
    if(@critera == "open")
      @gameusers = Gameuser.find_all_by_user_id(current_user.id)
      @gameids = @gameusers.map(&:game_id)
      if(@gameids.length == 0)
         @games = Game.find(:all, :conditions => ['state = ?', 0], :select => "id, slots, user_id, points_to_win, private, state") 

      else
         @games = Game.find(:all, :conditions => ['id NOT IN (?) AND state = ?', @gameids, 0], :select => "id, slots, user_id, points_to_win, private, state") 

      end
      @gamearray = [] 
      @gamestruct = Struct.new(:game, :players, :host, :is_czar, :havesubmit, :all_submit)
      @games.each do |game|
        @players = ActiveRecord::Base.connection.execute("SELECT gameusers.id, gameusers.score, users.name FROM gameusers JOIN users ON users.id = gameusers.user_id WHERE gameusers.game_id = #{game.id}")
        @host = User.find(game.user_id).name
        @is_czar = false
        @havesubmit = false
        @all_submit = false
        @temp = @gamestruct.new(game, @players, @host, @is_czar, @havesubmit, @all_submit)
        @gamearray.push(@temp)
      end
      render :status => 200,
             :json => { :success => true,
                        :data => {:games => @gamearray }}
    elsif(@critera == "in")
      @gameids = Gameuser.find_all_by_user_id(current_user.id).map(&:game_id)
      @games = Game.find_all_by_id_and_state(@gameids,[0,1])

      @gamearray = [] 
      @gamestruct = Struct.new(:game, :players, :host, :is_czar, :havesubmit, :all_submit)
      @games.each do |game|
        @players = ActiveRecord::Base.connection.execute("SELECT gameusers.id, gameusers.score, users.name, gameusers.user_id FROM gameusers JOIN users ON users.id = gameusers.user_id WHERE gameusers.game_id = #{game.id}")
        @host = User.find(game.user_id).name
        @is_czar = false
        @havesubmit = false
        @all_submit = false
        @gameuser = game.gameusers.find_by_user_id(current_user.id)
        if(game.state != 0)
          @is_czar = (current_user.id == game.gameusers[game.user_turn].user_id) 
          @havesubmit = game.whitecardinplay.find_all_by_gameuser_id(@gameuser.id) != []  
          @numsubmit = game.whitecardinplay.map(&:gameuser_id).uniq.length
          if @numsubmit == (game.slots - 1)
            @all_submit = true
          end
        end    
        @temp = @gamestruct.new(game, @players, @host, @is_czar, @havesubmit, @all_submit)
        @gamearray.push(@temp)
      end
      render :status => 200,
             :json => { :success => true,
                        :data => {:games => @gamearray }}
    elsif (@critera == "ended")
      @gameids = Gameuser.find_all_by_user_id(current_user.id).map(&:game_id)
      @games = Game.find_all_by_id_and_state(@gameids,2)
      @gamearray = [] 
      @gamestruct = Struct.new(:game, :players, :host, :is_czar, :havesubmit, :all_submit)
      @games.each do |game|
        @players = ActiveRecord::Base.connection.execute("SELECT gameusers.id, gameusers.score, users.name, gameusers.user_id FROM gameusers JOIN users ON users.id = gameusers.user_id WHERE gameusers.game_id = #{game.id}")
        @host = User.find(game.user_id).name
        @is_czar = false
        @havesubmit = false
        @all_submit = false
        @temp = @gamestruct.new(game, @players, @host, @is_czar, @havesubmit, @all_submit)
        @gamearray.push(@temp)
      end
      render :status => 200,
             :json => { :success => true,
                        :data => {:games => @gamearray }}
      
    elsif(@critera == "host")
      @games = Game.find(:all, :conditions => { :user_id => current_user.id}, :select => "id, slots, user_id, points_to_win, private, state") 

       @gamearray = [] 
      @gamestruct = Struct.new(:game, :players, :host, :is_czar, :havesubmit, :all_submit)
      @games.each do |game|
        @players = ActiveRecord::Base.connection.execute("SELECT gameusers.id, gameusers.score, users.name, gameusers.user_id FROM gameusers JOIN users ON users.id = gameusers.user_id WHERE gameusers.game_id = #{game.id}")
        @host = User.find(game.user_id).name
        @temp = @gamestruct.new(game, @players, @host, false, false, false)
        @gamearray.push(@temp)
      end
      render :status => 200,
             :json => { :success => true,
                        :data => {:games => @gamearray }}
    else 
      render :status => 404,
             :json => { :success => false,
                        :info => @critera,
                        :data => {} }
    end
  end

  def adduser
    @game = Game.find(params[:id])
    if @game      
      if @game.state == 0
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
                ActiveRecord::Base.transaction do
                  @listusers.each do |gameuser|
                    @game.notifications.create(:user_id => gameuser.user_id, :message => 'A game has started', :status => 1)
                  end
                end
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
    if !(@game.state == 2)
      @game.removeuser(current_user.id)
      render :status => 200,
             :json => { :success => true,
                        :info => "User deleted", 
                        :data => {} }
    else
      render :status => 409,
             :json => { :success => false,
                        :info => "Game is closed",
                        :data => {} }
    end   
  end

  def gethand
    @game = Game.find(params[:id])
    @gameuser = Gameuser.find_by_game_id_and_user_id(@game.id, current_user.id)
    @ids = @game.whitecardinhand.find_all_by_gameuser_id(@gameuser.id)
    @wcids = @ids.map(&:whitecard_id)
    @cards = Whitecard.find(@wcids).group_by(&:id)
    @text = @wcids.map{ |i| @cards[i].first }.map(&:text)
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
          if @game.whitecardinplay.map(&:gameuser_id).uniq.length == (@game.slots - 1)
            @game.notifications.create(:user_id => @game.gameusers[@game.user_turn].user_id, :message => "Time to pick", :status => 4)
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
          render :status => 200,
                 :json => { :success => true, 
                            :data => { } }
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
      @game.notifications.create(:user_id => @winninguser.user_id, :message => 'You won the last round', :status => 3)
      ActiveRecord::Base.transaction do
        @winninguser.update_attribute(:score, @winninguser.score + 1)
        @game.blackcardwon.create(:gameuser_id => @winninguser.id, :blackcard_id => @game.current_black) 
        @gamelast = @game.gamelasts.create(:gameuser_id => @winninguser.id, :blackcard_id => @game.current_black)
        @game.dealBlackCard!
        @game.whitecardinplay.each do |card|
          @gamelast.gamelastwhite.create(:gameuser_id => card.gameuser_id, :whitecard_id => card.whitecard_id)
          @game.whitecardindiscard.create(:whitecard_id => card.whitecard_id)
          card.destroy
        end
        if @winninguser.score == @game.points_to_win
          @game.update_attribute(:state, 2)
          @gameusers = @game.gameusers.order("score DESC")
          @game.update_attribute(:winner, @gameusers[0].user_id)
          @game.end!
          ActiveRecord::Base.transaction do
            @gameusers.each do |gu|
              @game.notifications.create(:user_id => gu.user_id, :message => 'The game has ended', :status => 5)
            end
          end
        else
          @game.next!
          if(@game.user_turn == @game.slots)
            @game.update_attribute(:user_turn, 0)
          end
          @gameusers = @game.gameusers

          ActiveRecord::Base.transaction do
            @gameusers.each do |gu|
              @game.notifications.create(:user_id => gu.user_id, :message => 'A new round has started', :status => 2)
            end
          end
        end
        render :status => 200,
               :json => { :success => true,
                          :data => { } }
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
    @submit = Struct.new(:name, :score, :czar, :submitted)
    @submitArray = []
    @game.gameusers.each do |gameuser|
      @card = @game.whitecardinplay.find_all_by_gameuser_id(gameuser.id).count >= 1
      @czar = gameuser.user_id == @game.gameusers[@game.user_turn].user_id
      @name = User.find(gameuser.user_id).name
      @temp = @submit.new(@name, gameuser.score, @czar, @card)
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
    @winningusername = User.find(Gameuser.find(@gamelast.gameuser_id).user_id).name
    @blacktext = Blackcard.find(@gamelast.blackcard_id).text
    @submit = Struct.new(:gameuser_id, :whitetext, :username)
    @submitArray = []
    @gameusers.each do |gameuser|
      @wcids = @gamelast.gamelastwhite.find_all_by_gameuser_id(gameuser.id).map(&:whitecard_id)
      @whitetexts = Whitecard.find(:all, :conditions => { :id => @wcids}).map(&:text)
      @username = User.find(gameuser.user_id).name
      @temp = @submit.new(gameuser.id, @whitetexts, @username)
      @submitArray.push(@temp)
    end 
    render :status => 200,
           :json => { :success => true,
                      :data => { :id => @gamelast.id,
                                 :black_card => @blacktext,
                                 :winninguser => @winningusername,
                                 :submitted => @submitArray}}
  end

  def history
    @game = Game.find(params[:id])
    @gameusers = @game.gameusers
    @gamelasts = @game.gamelasts
    @round = Struct.new(:black_card, :winninguser, :submitted, :id)
    @roundArray = []
    @submit = Struct.new(:gameuser_id, :whitetext, :username)
    @gamelasts.each do |gamelast|
      @blacktext = Blackcard.find(gamelast.blackcard_id).text
      @winningusername = User.find(Gameuser.find(gamelast.gameuser_id).user_id).name
      @submitArray = []
      @gameusers.each do |gameuser|
        @wcids = gamelast.gamelastwhite.find_all_by_gameuser_id(gameuser.id).map(&:whitecard_id)
        @whitetexts = Whitecard.find(:all, :conditions => { :id => @wcids}).map(&:text)
        @username = User.find(gameuser.user_id).name
        @temp = @submit.new(gameuser.id, @whitetexts, @username)
        @submitArray.push(@temp)
      end
      @tempRound = @round.new(@blacktext, @winningusername, @submitArray, gamelast.id)
      @roundArray.push(@tempRound)
    end
    render :status => 200,
           :json => { :success => true,
                      :data => { :rounds => @roundArray } }
  end





  def round
    @game = Game.find(params[:id])
    @gamestruct = Struct.new(:game, :players, :host, :is_czar, :havesubmit, :all_submit)
    @players = ActiveRecord::Base.connection.execute("SELECT gameusers.id, gameusers.score, users.name, gameusers.user_id FROM gameusers JOIN users ON users.id = gameusers.user_id WHERE gameusers.game_id = #{@game.id}")
    @host = User.find(@game.user_id).name
    @gameObj = @gamestruct.new(@game, @players, @host, false, false, false)
    @gameuser = Gameuser.find_by_game_id_and_user_id(@game.id, current_user.id)
    @is_czar = (current_user.id == @game.gameusers[@game.user_turn].user_id)
    @ids = []
    @wcids = []
    @text = []
    @submitCardArray = []
    @havesubmit = @game.whitecardinplay.find_all_by_gameuser_id(@gameuser.id) == []
    @all_submit = false
    if(!@is_czar)
      @ids = @game.whitecardinhand.find_all_by_gameuser_id(@gameuser.id)
      @wcids = @ids.map(&:whitecard_id)
      @card = Whitecard.find(@wcids).group_by(&:id)
      @text = @wcids.map{ |i| @card[i].first }.map(&:text)
    else
       @numsubmit = @game.whitecardinplay.map(&:gameuser_id).uniq.length
      if @numsubmit == (@game.slots - 1)
        @all_submit = true
      end
      @submitCard = Struct.new(:gameuser_id, :whitetext)
      @submitCardArray = [];
      @gameusers = @game.gameusers.where(['user_id <> ?', current_user])
      @gameusers.each do |gameuser|
         @ids = @game.whitecardinplay.find_all_by_gameuser_id(gameuser.id)
         @wcids = @ids.map(&:whitecard_id)
         @text = Whitecard.find(:all, :conditions => { :id => @wcids } ).map(&:text)
         @temp = @submitCard.new(gameuser.id, @text)
         @submitCardArray.push(@temp)
      end       
    end
    @submit = Struct.new(:name, :user_id, :score, :czar, :submitted)
    @submitArray = []    
    @game.gameusers.each do |gameuser|
      @card = @game.whitecardinplay.find_all_by_gameuser_id(gameuser.id).count >= 1
      @czar = gameuser.user_id == @game.gameusers[@game.user_turn].user_id
      @user = User.find(gameuser.user_id)
      @name = @user.name
      @user_id = @user.id
      @temp = @submit.new(@name, @user_id, gameuser.score, @czar, @card)
      @submitArray.push(@temp) 
    end
    @blackcard = Blackcard.find(@game.current_black)
    render :status => 200,
           :json => { :success => true,
                      :data => { :game => @gameObj,
                                 :havesubmit => @havesubmit, 
                                 :is_czar => @is_czar, 
                                 :hand => {:ids => @ids.map(&:id),
                                           :texts => @text},
                                 :submitted => @submitCardArray,
                                 :all_submit => @all_submit,
                                 :score => @submitArray,
                                 :black_card => { :text => @blackcard.text,
                                                  :numwhite => @blackcard.numwhite}
                                                  } 
                                                }




  end    
end