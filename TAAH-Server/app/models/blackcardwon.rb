class Blackcardwon < ActiveRecord::Base
	belongs_to :game
	belongs_to :user
	has_one :blackcard
  attr_accessible :blackcard_id, :game_id, :user_id
end
