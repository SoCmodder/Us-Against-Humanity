class Gameuser < ActiveRecord::Base
  belongs_to :user
  belongs_to :game
  attr_accessible :game_id, :user_id

  def leave!
  	@game = Game.find(:game_id)
  	if @game.state == nil
  		#Delete game user
  	else
  		#Delete game user and decrement game slot
  	end
  end
end
