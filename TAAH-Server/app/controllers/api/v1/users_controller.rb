class Api::V1::UsersController < ApplicationController
  skip_before_filter :verify_authenticity_token,
                     :if => Proc.new { |c| c.request.format == 'application/json' }

  # Just skip the authentication for now
  before_filter :authenticate_user!

  respond_to :json

  def find
  	@user = User.find(params[:id])
    render :status => 200,
           :json => { :success => true,
                      :data => { 
                        :user => @user
                        }}
  	rescue ActiveRecord::RecordNotFound
      render :status => 404,
            :json => { :success => false,
                       :info => 'Not Found',
                        :data => {} }
  end

  def email
    @user = User.find_by_email(params[:email])
    rescue ActiveRecord::RecordNotFound
      render :status => 404,
             :json => { :success => false,
                        :info => 'Not Found',
                        :data => {} }
  end

  def who
    render :status => 200,
           :json => { :success => true, 
           :info => 'User',
           :data => { :user => current_user }}
  end

  def notifications
    @notifications = User.find(current_user.id).notifications    
    render :status => 200,
           :json => { :success => true,
                      :data => { :notifications => @notifications }
                    }
    @notifications.destroy_all

  end

  def stats
    @user = User.find(params[:id])
    @numplay = Gameuser.find_all_by_user_id(@user.id).count
    @numwin = Game.find_all_by_winner(@user.id).count
    @fav = nil
    if(@user.userstat != nil)
      @fav = Whitecard.find(@user.userstat.whitecard_id).text
    end
    render :status => 200,
           :json => { :success => true,
                      :data => {
                        :played => @numplay,
                        :won => @numwin,
                        :favorite => @fav
                        }}                        
  end

  def getwhite
    @whitecards = Whitecard.all
    render :status => 200,
           :json => { :success => true,
                      :data => { :whitecards => @whitecards}}
  end

  def setwhite
    @user = User.find(current_user.id)
    if @user.userstat != nil
      @user.userstat.update_attribute(:whitecard_id, params[:id])
    else
      Userstat.create(:whitecard_id => params[:id], :user_id => @user.id)
    end
    render :status => 200,
           :json => { :success => true,
                      :data => {}}
  end

end