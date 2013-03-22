class CreateWhitecardinhands < ActiveRecord::Migration
  def change
    create_table :whitecardinhands do |t|
      t.integer :game_id
      t.integer :whitecard_id
      t.integer :gameuser_id

      t.timestamps
    end
  end
end
