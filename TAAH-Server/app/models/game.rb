class Game < ActiveRecord::Base
  belongs_to :user
  has_many :gameusers
  has_many :whitecardindeck
  has_many :whitecardinhand
  has_many :whitecardinplay
  has_many :whitecardindiscard
  has_many :blackcardindeck
  has_many :blackcardwon

  attr_accessible :points_to_win, :private, :slots

  validates_presence_of :points_to_win, :slots

  default_scope order('state DESC')

  def start!
  	update_column(:state, 1)
  	update_column(:user_turn, 0)
    @allcards = Whitecard.all.map(&:id)
    @allcards.each do |card|
      self.whitecardindeck.create(:whitecard_id => card)
    end
    @allcards = Blackcard.all.map(&:id)
    @allcards.each do |card|
      self.blackcardindeck.create(:blackcard_id => card)
    end
    self.gameusers.each do |gameuser|
      hand = self.whitecardindeck.all.sample(7)
      hand.each do |card|
        self.whitecardinhand.create(:gameuser_id => gameuser.id, :whitecard_id => card.whitecard_id)
        card.destroy
      end
    end

    self.dealBlackCard!
    
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

  def dealBlackCard!
    @blackcard = self.blackcardindeck.all.sample(1).first
    update_column(:current_black, @blackcard.blackcard_id)
    @blackcard.destroy
  end





end
