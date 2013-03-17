class CreateTasks < ActiveRecord::Migration
  def change
    create_table :tasks do |t|
      t.integer :user_id
      t.string :title
      t.boolean :completed

      t.timestamps
    end
  end
end
