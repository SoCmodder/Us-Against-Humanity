class Userstat < ActiveRecord::Base
	belongs_to :user

  attr_accessible :user_id, :whitecard_id
end
