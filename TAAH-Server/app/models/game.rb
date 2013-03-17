class Game < ActiveRecord::Base
  belongs_to :user
  has_many :gameusers
  has_many :whitecardindiscards
  has_many :whitecardsinplays
  has_one :blackcard
  has_many :blackcardwons

  attr_accessible :points_to_win, :private, :slots

  validates_presence_of :points_to_win, :slots

  default_scope order('state DESC')

  def start!
  	update_column(:state, 1)
  	update_column(:user_turn, 0)
  end

  def next!
  	if :user_turn+1 == :slots
  		update_column(:user_turn,0)
  	else
  		update_column(:user_turn,:user_turn+1)
  	end
  end

  def removeuser(*p)
    @user_id = *p
    self.gameusers.find_by_user_id(@user_id).destroy
    if state == 1
      update_column(:slots, self.slots - 1)
      if @user_id == self.user_id || self.slots == 1
        update_column(:state, 2)
      end
    end
  end       



end
