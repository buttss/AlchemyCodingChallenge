# AlchemyCodingChallenge

## Retrospective
* This was my first time using Room. It was pretty easy to get up and running, and it worked really well for the Item model returned from the API
* Using Koin might've been overkill, but it did help me quickly create new objects and not have to worry about plugging in the right implementations. This is one area I'm still a little unsure of my implementation. I feel like I abuse the single declaration too much.
* I wish I could've came up with a better solutions for the comments section, but I wasn't sure how to handle the HTML text of the comments in a native fashion.
* Overall, I'm fairly happy with the resulting app. It's not the prettiest, but it's usable.

## Future Improvements
* Satisfy all Bonus requirements
* Use native components to display the comments rather than a WebView. Due to time constraints and lack of a better implementation, I just went with building out an HTML string for the comments section
* Cache comments
* Improve UI/UX
* Better error handling