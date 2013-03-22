class CreateBlackcards < ActiveRecord::Migration
  def change
    create_table :blackcards do |t|
      t.string :text
      t.integer :numwhite

      t.timestamps
    end
  end
end
