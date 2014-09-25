from re import search
from os import path, listdir, remove
from shutil import copyfile

def main():
  code_dir = path.realpath('./Code')
  print 'code_dir = {0}'.format(code_dir)

  for file_name in listdir(code_dir):
    se = search(r'Machine Problem 1 \(Part 1\)_(.*)_attempt_(.*)_(QLearningAgent|QAgent).java', file_name)
    if se:
      agent_name = se.group(3)
      print 'NetID: {0}, Agent: {1}'.format(se.group(1), agent_name)

      copyfile(path.realpath('./Code/{0}'.format(file_name)), '{0}.java'.format(agent_name))
      
      remove('{0}.java'.format(agent_name))

if __name__ == '__main__':
  main()
