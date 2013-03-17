class CreateGames < ActiveRecord::Migration
  def change
    create_table :games do |t|
      t.integer :user_id
      t.integer :slots
      t.integer :points_to_win
      t.boolean :private
      t.integer :state, default => 0 
      t.integer :user_turn
      t.integer :winner
      t.integer :current_black

      t.timestamps
    end
  end
end
