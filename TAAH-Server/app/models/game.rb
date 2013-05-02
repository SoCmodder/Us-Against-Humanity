class Game < ActiveRecord::Base
  belongs_to :user
  has_many :gameusers
  has_many :whitecardindeck
  has_many :whitecardinhand
  has_many :whitecardinplay
  has_many :whitecardindiscard
  has_many :blackcardindeck
  has_many :blackcardwon
  has_many :gamelasts
  has_many :notifications

  attr_accessible :points_to_win, :private, :slots

  validates_presence_of :points_to_win, :slots
  validates_inclusion_of :slots, :in => 3..18, :message => "must be between 3 and 18"

  default_scope order('state DESC')

  def start!
  	update_column(:state, 1)
  	update_column(:user_turn, 0)
    @allwhitecards = Whitecard.all.map(&:id);
    @allblackcards = Blackcard.all.map(&:id);
    ActiveRecord::Base.transaction do
      @allwhitecards.each do |card|
        self.whitecardindeck.create(:whitecard_id => card)
      end
      @allblackcards.each do |card|
        self.blackcardindeck.create(:blackcard_id => card)
      end
      self.gameusers.each do |gameuser|
        hand = self.whitecardindeck.all.sample(10)
        hand.each do |card|
          self.whitecardinhand.create(:gameuser_id => gameuser.id, :whitecard_id => card.whitecard_id)
          card.destroy
        end
      end
    end

    self.dealBlackCard!
    
  end

  def next!
  	if self.user_turn+1 == self.slots
  		update_column(:user_turn,0)
  	else
  		update_column(:user_turn, self.user_turn+1)
  	end
  end

  def removeuser(*p)
    @user_id = *p
    self.gameusers.find_by_user_id(@user_id).destroy
    if state == 1
      update_column(:slots, self.slots - 1)
      if @user_id == self.user_id || self.slots == 2
        update_column(:state, 2)
        @gameusers = self.gameusers.order("score DESC")
        update_column(:winner, @gameusers[0].user_id)
      end
    end
  end       

  def dealBlackCard!
    @blackcard = self.blackcardindeck.all.sample(1).first
    update_column(:current_black, @blackcard.blackcard_id)
    @blackcard.destroy
  end

  def end!
    ActiveRecord::Base.transaction do
      self.whitecardindeck.each do |card|
        card.destroy
      end
      self.whitecardinplay.each do |card|
        card.destroy
      end
      self.whitecardinhand.each do |card|
        card.destroy
      end
      self.whitecardindiscard.each do |card|
        card.destroy
      end
      self.blackcardindeck.each do |card|
        card.destroy
      end
      self.blackcardwon.each do |card|
        card.destroy
      end
    end
  end
  
end
