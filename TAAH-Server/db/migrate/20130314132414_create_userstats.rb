class CreateUserstats < ActiveRecord::Migration
  def change
    create_table :userstats do |t|
      t.integer :user_id
      t.integer :gamesplayed
      t.integer :gameswon
      t.integer :blackcard_id

      t.timestamps
    end
  end
end
