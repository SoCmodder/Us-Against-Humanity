class User < ActiveRecord::Base
	has_many :tasks
  has_many :games
  has_many :gameusers
  has_many :notifications
  has_one :userstat
  # Include default devise modules. Others available are:
  # :token_authenticatable, :confirmable,
  # :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable,
         :confirmable, :token_authenticatable

  # Setup accessible (or protected) attributes for your model
  attr_accessible :name, :email, :password, :password_confirmation, :remember_me, :id
  # attr_accessible :title, :body

  before_save :ensure_authentication_token

  def skip_confirmation!
  	self.confirmed_at = Time.now
  end
end
