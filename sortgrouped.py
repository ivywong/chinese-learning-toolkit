import sys, os, operator

'''
Sorts grouped characters from greatest number of words to least.
'''
def sort_grouped(input, output):
	try:
		with open(input, 'r') as inputf, open(output, 'w') as outputf:
			words = {}
			lineDelim = ": "
			wordDelim = ", "

			for line in inputf:
				line_info = line.strip('\n').split(lineDelim)

				if len(line_info) == 2:
					words[line_info[0]] = line_info[1].split(wordDelim)
				else:
					print("Line delim wrong. Please change.")
					sys.exit(1)

			sorted_list = sorted(words.items(), key=lambda x: len(x[1]), reverse=True)
			for s in sorted_list:
				row = '%s: %s\n'%(s[0].decode('UTF-8').encode('UTF-8'), ", ".join(s[1]).decode('UTF-8').encode('UTF-8'))
				#row = '%s: %s\n'%(s[0].decode('UTF-8').encode('UTF-8'), len(s[1]))
				#print row
				outputf.write(row)

	except IOError, err:
		print("IOError.")
		print err
	

if __name__ == "__main__":
	if len(sys.argv) == 3:
		append_to_row(sys.argv[1], sys.argv[2])
	else:
		print("Usage: %s path/to/input path/to/output"%(sys.argv[0]))
		sys.exit(1)