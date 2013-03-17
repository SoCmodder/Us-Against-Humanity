class CreateWhitecards < ActiveRecord::Migration
  def change
    create_table :whitecards do |t|
      t.string :title
      t.string :text

      t.timestamps
    end
  end
end
