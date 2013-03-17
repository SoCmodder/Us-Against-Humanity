object false
node (:success) { true }
node (:info) { 'Game User created!' }
child :data do
  child @gameuser do
    attributes :id, :game_id, :user_id
  end
end