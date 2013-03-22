class CreateBlackcardindecks < ActiveRecord::Migration
  def change
    create_table :blackcardindecks do |t|
      t.integer :game_id
      t.integer :blackcard_id

      t.timestamps
    end
  end
end
