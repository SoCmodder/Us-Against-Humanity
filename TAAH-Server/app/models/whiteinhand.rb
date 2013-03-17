class Whiteinhand < ActiveRecord::Base

	belongs_to :whitecard
	belongs_to :user
	belongs_to :game
  attr_accessible :game_id, :user_id, :whitecard_id
end
