class Userstat < ActiveRecord::Base
  attr_accessible :blackcard_id, :gamesplayed, :gameswon, :user_id
end
