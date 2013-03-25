class CreateGamelastwhites < ActiveRecord::Migration
  def change
    create_table :gamelastwhites do |t|
      t.integer :gamelast_id
      t.integer :whitecard_id
      t.integer :gameuser_id

      t.timestamps
    end
  end
end
