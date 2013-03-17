object false
node (:success) { true }
node (:info) { 'Game created!' }
child :data do
  child @game do
    attributes :id
  end
end