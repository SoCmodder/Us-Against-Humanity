class CreateBlackcardwons < ActiveRecord::Migration
  def change
    create_table :blackcardwons do |t|
      t.integer :game_id
      t.integer :gameuser_id
      t.integer :blackcard_id

      t.timestamps
    end
  end
end
