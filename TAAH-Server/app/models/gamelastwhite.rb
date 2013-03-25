class Gamelastwhite < ActiveRecord::Base
	belongs_to :gamelast
  attr_accessible :gamelast_id, :gameuser_id, :whitecard_id
end
