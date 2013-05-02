class AddStateToGames < ActiveRecord::Migration
  def change
    change_column :games, :state, :int, :default => 0
  end
end
