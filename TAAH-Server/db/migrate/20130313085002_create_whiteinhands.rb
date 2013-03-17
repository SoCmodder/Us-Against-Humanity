class CreateWhiteinhands < ActiveRecord::Migration
  def change
    create_table :whiteinhands do |t|
      t.integer :game_id
      t.integer :user_id
      t.integer :whitecard_id

      t.timestamps
    end
  end
end
