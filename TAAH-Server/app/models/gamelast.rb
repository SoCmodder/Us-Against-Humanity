class Gamelast < ActiveRecord::Base
	has_many :gamelastwhite
  attr_accessible :blackcard_id, :game_id, :gameuser_id
end
