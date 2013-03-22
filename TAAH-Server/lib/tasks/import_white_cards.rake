namespace :db do

  desc "load user data from csv"
  task :load_csv_data  => :environment do
    require 'csv'

    CSV.foreach("WhiteCard.csv") do |row|
			Whitecard.create(:text => row[0])
		end
	end
end