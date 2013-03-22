class Whitecardindeck < ActiveRecord::Base
	belongs_to :whitecard
	belongs_to :game
  attr_accessible :game_id, :whitecard_id
end
