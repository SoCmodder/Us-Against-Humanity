class Notification < ActiveRecord::Base
  belongs_to :user
  belongs_to :game
  attr_accessible :game_id, :message, :status, :user_id
end
