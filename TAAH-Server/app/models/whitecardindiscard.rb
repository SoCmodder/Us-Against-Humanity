class Whitecardindiscard < ActiveRecord::Base
	belongs_to :game
	belongs_to :whitecard
  attr_accessible :game_id, :whitecard_id
end
