// [MQH] 17 Jan 2016
package jekyll;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishedPost extends Post
{
	private String date;
	private static DateFormat postDateFormat = new SimpleDateFormat( "yyyy-MM-dd" ); // hh:mm:ss a" ); There is something wrong with time in Jekyll 3.0.1
	
	public PublishedPost( File file, Blog blog )
	{
		super( file, blog );
	}
	
	public PublishedPost( String title, Blog blog )
	{
		super( title, blog );
		
		this.date = postDateFormat.format( new Date() );
	}
	
	// Make a copy of a draft as a published post.
	public PublishedPost( Draft draft, Blog blog )
	{
		super( draft.getTitle(), blog );
		
		this.date = postDateFormat.format( new Date() );
		
		this.setContent( draft.getContent() );
		this.setTags( draft.getTags() );
		this.setCategories( draft.getCategories() );
	}

	public static String parseTitleFromFilename( String filename )
	{
		return Post.parseTitleFromFilename( filename, true );
	}
	
	public String getDate()
	{
		return this.date.split( " " )[ 0 ];
	}
	
	public String getTime()
	{
		return this.date.split( " " )[ 1 ];
	}
	
	public String getPostDateAsString()
	{
		return this.date;
	}
	
	public Date getPostDate()
	{
		try
		{
			return postDateFormat.parse( this.date );
		} catch ( ParseException e ) { e.printStackTrace(); }
		
		return null;
	}
	
	@Override
	public boolean save()
	{
		this.postDir = this.blog.getPostsDir();
		
		return super.save();
	}
	
	@Override
	protected void parseFrontMatter()
	{
		String loadedDate = this.frontMatter.get( "date" );
		
		if ( loadedDate == null )
			this.date = postDateFormat.format( new Date() );
		else
			this.date = loadedDate;	
	}

	@Override
	protected String generateFilename()
	{
		return this.getDate() + "-" + Post.getFilenameFromTitle( this.getTitle() );
	}

	@Override
	protected String generateFrontMatter()
	{
		String postFrontMatter = "date: " + this.getPostDateAsString() + "\n";
		
		postFrontMatter += "layout: post\n";
		
		return postFrontMatter;
	}

	@Override
	public PostType getType()
	{
		return PostType.PUBLISHED;
	}
}
