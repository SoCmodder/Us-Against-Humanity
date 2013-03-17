class CreateWhitecardinplays < ActiveRecord::Migration
  def change
    create_table :whitecardinplays do |t|
      t.integer :user_id
      t.integer :whitecard_id
      t.integer :game_id

      t.timestamps
    end
  end
end
