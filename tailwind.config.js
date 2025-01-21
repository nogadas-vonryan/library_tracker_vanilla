/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
	"./src/main/webapp/**/*.{html,js,jsp}",
    "./src/main/webapp/WEB-INF/views/**/*.jsp"
	],
  theme: {
    extend: {
      colors: {
        primary: '#7f1d1d',
        // primary: '#3730a3'
      }
    },
  },
  plugins: [
	require('daisyui'),
  ],
  daisyui: {
    themes: ["light"],
  },
}

