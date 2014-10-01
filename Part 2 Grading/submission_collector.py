# Author: Mingcheng Chen

from re import search
from os import makedirs, path, listdir
from shutil import copyfile

def main():
  submission_dir = path.realpath('./extracted');
  print 'submission_dir = {0}'.format(submission_dir)

  for (idx, file_name) in enumerate(sorted(listdir(submission_dir))):
    print 'processing file {0}: {1}'.format(idx + 1, file_name)

    se = search(r'Machine Problem 1 \(Part 2\)_(.*)_attempt', file_name)
    assert se

    net_id = se.group(1)
    package_path = path.realpath('./Collection/{0}'.format(net_id));

    if not path.exists(package_path):
      makedirs(package_path)

    new_file_path = path.join(package_path, file_name)
    copyfile(path.join(submission_dir, file_name), new_file_path)

if __name__ == '__main__':
  main()
