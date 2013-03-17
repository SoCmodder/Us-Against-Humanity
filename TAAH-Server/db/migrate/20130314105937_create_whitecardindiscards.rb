class CreateWhitecardindiscards < ActiveRecord::Migration
  def change
    create_table :whitecardindiscards do |t|
      t.integer :whitecard_id
      t.integer :game_id

      t.timestamps
    end
  end
end
