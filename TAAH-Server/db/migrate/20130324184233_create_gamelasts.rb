class CreateGamelasts < ActiveRecord::Migration
  def change
    create_table :gamelasts do |t|
      t.integer :game_id
      t.integer :blackcard_id
      t.integer :gameuser_id

      t.timestamps
    end
  end
end
