object false
node (:success) { true }
node (:info) { 'Task opened!' }
child :data do
  child @task do
    attrubutes :id, :title, :created_at, :completed
  end
end