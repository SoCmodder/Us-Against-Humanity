class Api::V1::UsersController < ApplicationController
  skip_before_filter :verify_authenticity_token,
                     :if => Proc.new { |c| c.request.format == 'application/json' }

  # Just skip the authentication for now
  before_filter :authenticate_user!

  respond_to :json

  def find
  	@user = User.find(params[:id])
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
end