class CreateUserstats < ActiveRecord::Migration
  def change
    create_table :userstats do |t|
      t.integer :user_id
      t.integer :whitecard_id

      t.timestamps
    end
  end
end
