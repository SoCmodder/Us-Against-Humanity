namespace :db do

  desc "load user data from csv"
  task :load_black_data  => :environment do
    require 'csv'

    CSV.foreach("BlackCard.csv") do |row|
			Blackcard.create(:text => row[0],
					:numwhite => row[1])
		end
	end
end