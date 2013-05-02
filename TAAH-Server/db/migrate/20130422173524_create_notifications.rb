class CreateNotifications < ActiveRecord::Migration
  def change
    create_table :notifications do |t|
      t.integer :user_id
      t.string :message
      t.integer :game_id
      t.integer :status

      t.timestamps
    end
  end
end
