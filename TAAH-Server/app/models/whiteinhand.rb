class Whiteinhand < ActiveRecord::Base
	belongs_to :game
	belongs_to :gameuser
	belongs_to :whitecard
	
  attr_accessible :game_id, :gameuser_id, :whitecard_id
  validates :game_id, :presence => true
  validates :gameuser_id, :presence => true
  validates :whitecard_id, :presence => true
end
