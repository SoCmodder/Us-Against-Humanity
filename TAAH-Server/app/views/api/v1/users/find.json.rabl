object false
node(:success) { true }
child :data do 
	child @user do
		attributes :id, :name, :email
	end
end
