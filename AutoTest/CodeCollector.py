# Author: Mingcheng Chen

import csv
from re import search
from os import path, listdir, remove, devnull
from shutil import copyfile
import subprocess
from subprocess import call, check_call, check_output, CalledProcessError

def main():
  code_dir = path.realpath('./Code')
  print 'code_dir = {0}'.format(code_dir)

  f_null = open(devnull, 'w')

  csv_file = open('test_result.csv', 'wb')
  csv_writer = csv.writer(csv_file, delimiter = ',', quotechar = '"')

  for (idx, file_name) in enumerate(sorted(listdir(code_dir))):
    se = search(r'Machine Problem 1 \(Part 1\)_(.*)_attempt_(.*)_(QLearningAgent|QAgent|qLearningAgent).java', file_name)
    if se:
      agent_name = se.group(3)
      print '#{2}, NetID: {0}, Agent: {1}'.format(se.group(1), agent_name, idx + 1)

      copyfile(path.realpath('./Code/{0}'.format(file_name)), '{0}.java'.format(agent_name))

      if call(['javac', '{0}.java'.format(agent_name)], stdout = f_null, stderr = subprocess.STDOUT) != 0:
        print 'Compilation failed.\n'
        csv_writer.writerow([se.group(1), agent_name, 'Compilation Error'])
        continue
      
      if call(['java', 'Tester', agent_name, '10000', '4', '100000'], stdout = f_null, stderr = subprocess.STDOUT) == 0:
        print 'Passed!\n'
        csv_writer.writerow([se.group(1), agent_name, 'Accepted'])
      else:
        print '\033[91mFailed!\n\033[0m'
        csv_writer.writerow([se.group(1), agent_name, 'Wrong Answer | Runtime Error'])
      
      remove('{0}.java'.format(agent_name))
      remove('{0}.class'.format(agent_name))

if __name__ == '__main__':
  main()
