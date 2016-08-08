import sys, os

'''
Appends all the words in a word-per-newline file as one row in a specified text
file, with the filename or a specified name as the row label.
'''
def append_to_row(input, output, *optional):
	try:
		with open(input, 'r') as inputf, open(output, 'a+') as outputf:
			words = []
			for line in inputf:
				words.append(line.strip('\n'))

			row = ", ".join(words)
			base = os.path.basename(input)
			filename = os.path.splitext(base)[0]

			if len(optional[0]) == 1:
				outputf.write("%s: %s\n"%(optional[0][0], row))
			else:
				outputf.write("%s: %s\n"%(filename, row))
	except IOError, err:
		print("IOError.")
		print err
	

if __name__ == "__main__":
	if len(sys.argv) >= 3:
		append_to_row(sys.argv[1], sys.argv[2], sys.argv[3:])
	else:
		print("Usage: %s path/to/input path/to/output <row_name>"%(sys.argv[0]))
		sys.exit(1)