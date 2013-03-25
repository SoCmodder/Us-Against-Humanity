class AddScoreToGameuser < ActiveRecord::Migration
  def change
    add_column :gameusers, :score, :integer, :default => 0
  end
end
