class CreateUserstatwhites < ActiveRecord::Migration
  def change
    create_table :userstatwhites do |t|
      t.integer :userstat_id
      t.integer :whitecard_id

      t.timestamps
    end
  end
end
